pipeline {
    agent any

    environment {
        IMAGE_NAME = "web-flux-demo"
        IMAGE_TAG  = "${BUILD_NUMBER}"
        DEPLOYMENT_NAME = "web-flux-deployment"
        CONTAINER_NAME  = "web-flux-container"
        K8S_NAMESPACE   = "default"
        DOCKER_CREDENTIALS_ID = "docker-hub-credentials" // Jenkins credential ID
    }

    stages {

        stage('Build Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t %IMAGE_NAME%:%IMAGE_TAG% ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'
                        bat "docker tag %IMAGE_NAME%:%IMAGE_TAG% %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%"
                        bat "docker push %DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG%"

                        // Remove old images locally: keep only latest 2
                        bat '''
                        for /F "skip=2 tokens=1" %%i in ('docker images %IMAGE_NAME% --format "{{.ID}}"') do docker rmi -f %%i
                        '''

                        bat 'docker logout'
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    bat "kubectl set image deployment/%DEPLOYMENT_NAME% %CONTAINER_NAME%=%DOCKER_USER%/%IMAGE_NAME%:%IMAGE_TAG% -n %K8S_NAMESPACE%"
                    bat "kubectl rollout status deployment/%DEPLOYMENT_NAME% -n %K8S_NAMESPACE%"
                }
            }
        }

    }

    post {
        success {
            echo "Deployment successful: %IMAGE_NAME%:%IMAGE_TAG%"
        }
        failure {
            echo "Pipeline failed. Check logs!"
        }
    }
}

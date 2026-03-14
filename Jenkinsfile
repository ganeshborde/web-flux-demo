pipeline {
    agent any

    environment {
        IMAGE_NAME = "web-flux-demo"
        IMAGE_TAG  = "${BUILD_NUMBER}"
        DEPLOYMENT_NAME = "web-flux-deployment"
        CONTAINER_NAME  = "web-flux-container"
        K8S_NAMESPACE   = "default" // change if using another namespace
    }

    stages {

        stage('Build Maven') {
            steps {
                bat 'mvn clean package -DskipTests' // skip tests optionally for faster build
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
                    // If using local Docker Desktop, push is optional
                    // For remote registry, uncomment below and configure registry login
                    // bat "docker tag %IMAGE_NAME%:%IMAGE_TAG% myregistry.com/%IMAGE_NAME%:%IMAGE_TAG%"
                    // bat "docker push myregistry.com/%IMAGE_NAME%:%IMAGE_TAG%"
                    echo "Image built: %IMAGE_NAME%:%IMAGE_TAG%"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Update deployment image in Kubernetes
                    bat "kubectl set image deployment/%DEPLOYMENT_NAME% %CONTAINER_NAME%=%IMAGE_NAME%:%IMAGE_TAG% -n %K8S_NAMESPACE%"

                    // Optional: rollout status to wait for deployment completion
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

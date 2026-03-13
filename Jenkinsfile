pipeline {
    agent any

    stages {

        stage('Build Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t web-flux-demo:%BUILD_NUMBER% .'
            }
        }

        stage('Deploy Pod') {
            steps {
                bat "kubectl set image deployment/web-flux-deployment web-flux-container=web-flux-demo:15"
            }
        }

    }
}

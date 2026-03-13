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
                bat "kubectl set image pod/web-flux-demo-pod web-flux-container=web-flux-demo:%BUILD_NUMBER%"
            }
        }

    }
}

pipeline {
    agent any

    triggers {
        pollSCM('H/1 * * * *')
    }

    stages {

        stage('Build Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Clean Old Docker Images') {
            steps {
                bat '''
                docker images -q web-flux-demo > images.txt
                for /f %%%%i in (images.txt) do docker rmi -f %%%%i
                del images.txt
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t web-flux-demo:%BUILD_NUMBER% .'
            }
        }
    }
}

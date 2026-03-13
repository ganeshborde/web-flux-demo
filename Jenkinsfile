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

        stage('Cleanup Docker') {
            steps {
                bat 'docker image prune -f'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t web-flux-demo:%BUILD_NUMBER% .'
            }
        }
    }
}

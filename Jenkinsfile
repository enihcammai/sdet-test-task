pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Tests') {
            steps {
                bat 'docker-compose up -d selenoid'
                bat 'timeout 30 /NOBREAK >nul & curl http://localhost:4444/status'
                bat 'docker-compose up -d test-runner'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    bat 'docker-compose down'
                }
            }
        }
    }
}
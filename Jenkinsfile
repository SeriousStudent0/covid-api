pipeline {
    agent any

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t covid-api .'
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                script {
                    // Tag Docker image
                    sh 'docker tag covid-api jet00000/covid-api:0.0.8'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push Docker image to registry
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        sh 'docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD'
                        sh 'docker push jet00000/covid-api:0.0.8'
                        sh 'docker logout'
                    }
                }
            }
        }

        stage('Run Docker Compose') {
            steps {
                script {
                    // Install Docker Compose
                    sh 'curl -L https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose'
                    sh 'chmod +x /usr/local/bin/docker-compose'

                    // Run Docker Compose
                    sh 'docker-compose up -d'
                }
            }
        }
    }
}

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

        withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials-id', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
            stage('Push Docker Image') {
                steps {
                    script {
                        // Login to Docker Hub
                        sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}"
                        
                        // Push Docker image to registry
                        sh 'docker push jet00000/covid-api:0.0.8'
                        
                        // Logout from Docker Hub
                        sh 'docker logout'
                    }
                }
            }
        }

        stage('Run Docker Compose') {
            steps {
                script {
                    // Run Docker Compose
                    sh 'docker-compose up -d'
                }
            }
        }
    }
}

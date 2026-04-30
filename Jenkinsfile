pipeline {
    agent any
    
    stages {
        stage ('code') {
            steps {
                git branch: 'main', url: 'https://github.com/Kajjayamsriram/cab-booking.git'
            }
        }
        stage ('build') {
            steps {
                sh '''
                cp -r src pom.xml alpineDockerfile
                cd alpineDockerfile
                docker build -t sriramk16/$tag .
                '''
            }
        }
        stage ('registry') {
            steps {
                withDockerRegistry(credentialsId: 'docker-hub', url: 'https://index.docker.io/v1/') {
                    sh 'docker push sriramk16/$tag'
                }
            }
        }
        stage ('deploy') {
            steps {
                sh 'docker run -d --name $cont -p $port:8080 sriramk16/$tag'
            }
        }
    }
    post {
        always {
            mail to: "your-email@gmail.com",
            subject: "INFO - ${currentBuild.fullDisplayName}",
            body: "Conatiner deployment is ${currentBuild.currentResult}. Please checkout at ${env.BUILD_URL}"
        }
    }
}

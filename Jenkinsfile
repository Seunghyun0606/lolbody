pipeline {
    agent any
    
    stages {
        stage('Pull') {
            steps {
                git credentialsId: 'git_id', url: 'https://lab.ssafy.com/s03-webmobile1-sub3/s03p13b105.git'
            }
        }
        stage('Build') {
            steps {
                dir('/'){
                    sh 'ls'
                }
                dir('back/mongoApi'){
                    sh 'mvn clean package -Dmaven.test.skip=true'
                    sh 'ls'
                    script{
                        try {
                            sh 'ls'
                            // sh 'sudo cp -r csv/ target/'
                            // sh 'sudo cp SummonerValue.py target/'
                            // sh 'sudo cp Dockerfile target/'
                            sh 'docker stop spring-develop'
                            sh 'docker rm spring-develop'
                            // sh 'docker rmi spring-develop:0.1'
                            // sh 'cd target/'
                            // sh 'docker build -t spring-develop:0.1'
                            }catch(e){
                        }
                    }
                    sh 'docker run -d --name spring-develop -p 8888:8888 spring-develop:0.1'

                }
                dir('front'){
                    sh 'yarn install'
                    sh 'yarn build'
                    sh 'sudo cp -r dist /home/ubuntu/hrtest/s03p13b105/nginx/var/www/html/'
                }
                mattermostSend color: '#439FE0', icon: "https://jenkins.io/images/logos/jenkins/jenkins.png", message: "Build Started: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
            }
        }
    }  
}

pipeline {
    agent any
    
    stages {
        stage('Pull') {
            steps {
                git credentialsId: 'git_id', url: 'https://lab.ssafy.com/s03-webmobile1-sub3/s03p13b105.git'
                sh 'pwd'
                sh 'ls'
            }
        }
        stage('Build') {
            steps {
                dir('back/mongoApi'){
                    sh 'mvn clean package -Dmaven.test.skip=true'
                    script{
                        try {
                            sh 'docker stop spring'
                            sh 'docker rm spring'
                            }catch(e){
                        }
                    }
                    sh 'docker run -d --name spring -p 8889:8888 springboot:0.1'

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

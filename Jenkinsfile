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
                dir('back/mongoApi'){
                    sh 'mvn package -Dmaven.test.skip=true'
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
                    sh 'cp -r dist /home/ubuntu/hrtest/s03p13b105/nginx/var/www/html/'
                }
            }
        }
    }  
}
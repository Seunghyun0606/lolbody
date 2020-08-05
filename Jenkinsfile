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
                    sg 'java -version'
                    sg 'mvn -version'
                    sg 'mvn package'
                    script{
                        try {
                            sh 'sudo docker stop spring'
                            sh 'sudo docker rm spring'
                        }catch(e){
                        }
                    }
                    sh 'sudo docker run -i -t --name spring -p 8889:8888 springboot:0.1'
                }
                dir('front'){
                    sh 'yarn install'
                    sh 'yarn build'
                    sh 'cp dist /usr/share/nginx/html'
                }
            }
        }
    }  
}
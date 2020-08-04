pipeline {
    agent any
    
    stages {
        stage('Pull') {
            steps {
                git config --global credential.username {USERNAME}
                git config --global credential.helper "!echo password={PASSWORD}; echo"
                git 'https://lab.ssafy.com/s03-webmobile1-sub3/s03p13b105.git'
            }
        }
        stage('Build') {
            steps {
                dir('back/mongoApi'){
                    sh 'mvn clean install -DskipTests'
                    sh 'mvn package'
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
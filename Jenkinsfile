pipeline {
    agent any
    
    stages {
        stage('Pull') {
            steps {
                git credentialsId: 'git_id', url: 'https://lab.ssafy.com/s03-webmobile1-sub3/s03p13b105.git'
                sh 'git checkout develop'
            }
        }
        stage('Build') {
            steps {
                dir('front'){
                    sh 'yarn install'
                    sh 'yarn build'
                    sh 'sudo cp -r dist /home/ubuntu/hrtest/s03p13b105/nginx/var/www/html/'
                }
                dir('back/mongoApi'){
                    sh 'mvn clean package -Dmaven.test.skip=true'
                    script{
                        try {
                            
                            sh 'docker stop spring-develop'
                            sh 'docker rm spring-develop'
                            sh """
                                sudo cp -r csv/ target/
                                sudo cp SummonerValue.py target/
                                sudo cp Dockerfile target/
                                cd target/
                            """
                            sh 'sudo docker build -t spring-develop:0.1 .'
                            }catch(e){
                        }
                    }
                    sh 'docker run -d --name spring-develop -p 8888:8888 spring-develop:0.1'

                }
                mattermostSend color: '#439FE0', icon: "https://jenkins.io/images/logos/jenkins/jenkins.png", message: "Build Started: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
            }
        }
    }  
}

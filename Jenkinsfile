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
                        def springC = docker.container('spring')
                        springC.stop()
                    }
                    def makespringC = docker.image("springboot:0.1").run("--name spring -i -t --publish 8889:8888")
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
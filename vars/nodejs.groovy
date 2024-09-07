def lintchecks(){
        sh "whoami"
        sh "echo *** Lint check starting for ${component} ***"
        sh "npm i jslint"
        sh "/home/centos/node_modules/jslint/bin/jslint.js server.js || true"
        sh "echo *** Lint check completed ***"
}

def call('component'){
        pipeline{
                agent any
                stages{
                    stage("lintchecks"){
                        steps{
                                script {
                                     lintchecks('component')           
                }
            }
        }
        }
}
}               
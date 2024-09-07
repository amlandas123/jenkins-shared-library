def lintchecks(){
        sh "whoami"
        sh "echo *** Lint check starting for ${component} ***"
        sh "npm install jslint"
        sh "node_modules/jslint/bin/jslint.js server.js || true"
        sh "echo *** Lint check completed for ${component} ***"
}

def call(){
        pipeline{
                agent{
                        label "node"
                }
                stages{
                    stage("lintchecks"){
                        steps{
                                script {
                                     lintchecks()           
                                       }
                                }
                
                        }
                    stage("Static code analysis"){
                        steps{
                                sh "echo static code analysis in process"
                        }
                        
                    }    
                }

}
}               
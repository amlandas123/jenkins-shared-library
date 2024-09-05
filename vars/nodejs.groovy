def lintchecks(){
        sh "whoami"
        sh "echo *** Lint check starting ***"
        sh "npm i jslint"
        sh "/home/centos/node_modules/jslint/bin/jslint.js server.js || true"
        sh "echo *** Lint check completed ***"
}
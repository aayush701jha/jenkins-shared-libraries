def call(String appName) {
    script {
        def newPort = sh(script: "if [ \"\$(cat /tmp/active_port)\" = \"8001\" ]; then echo 8002; else echo 8001; fi", returnStdout: true).trim()

        echo "🔄 Deploying new instance on port ${newPort}"

        sh """
            pm2 start npm --name '${appName}-${newPort}' -- start -- --port=${newPort} || pm2 restart '${appName}-${newPort}'
            pm2 save
        """

        sh "echo ${newPort} > /tmp/active_port"

        echo "🚀 New version deployed on ${newPort}, updating Nginx..."
        sh "sudo nginx -s reload"
    }
}

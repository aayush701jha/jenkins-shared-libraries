def call(String appName) {
    script {
        // Agar `/tmp/active_port` file nahi hai, toh default 8001 le
        def activePort = sh(script: "[ -f /tmp/active_port ] && cat /tmp/active_port || echo '8001'", returnStdout: true).trim()
        
        // Next Port decide karne ka logic
        def newPort = (activePort == "8001") ? "8002" : "8001"

        echo "🔄 Deploying new instance on port ${newPort}"

        // Purana process na hataye, bas naya start kare
        sh """
            pm2 start npm --name '${appName}-${newPort}' -- start -- --port=${newPort} || pm2 restart '${appName}-${newPort}'
            pm2 save
        """

        // Naya active port update kare
        sh "echo ${newPort} > /tmp/active_port"

        echo "🚀 New version deployed on ${newPort}, updating Nginx..."
        sh "sudo nginx -s reload"
    }
}

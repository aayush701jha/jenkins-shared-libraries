def call(String appName) {
    script {
        sh "pm2 delete ${appName} || true"
        sh """
            pm2 start npm --name '${appName}' -- start
            pm2 save
        """
        echo "✅ Deployment successful for ${appName}"
    }
}

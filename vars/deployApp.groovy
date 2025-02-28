def call(String appName) {
    script {
        sh """
            if pm2 list | grep -q ${appName}; then
                pm2 reload ${appName}
            else
                pm2 start npm --name '${appName}' -- start
            fi
            pm2 save
        """
        echo "🚀 Zero-Downtime Deployment Done for '${appName}'!"
    }
}

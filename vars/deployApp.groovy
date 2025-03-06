def call(String appName) {
    script {
        sh """
            if pm2 list | grep -q '${appName}'; then
                echo "🔄 Process Exists, Reloading..."
                pm2 reload ${appName} --update-env --wait-ready --kill-timeout 3000
            else
                echo "🚀 Process Not Found, Starting New One..."
                pm2 start npm --name '${appName}' -- start
            fi
            pm2 save
        """
        echo "✅ Application '${appName}' Deployed Successfully!"
    }
}

@Library(["vpp", "bop@auto-channel","bop-release"]) _


    stage('TestGit') 
    {
        util.AutoChannel.isGitFlow = true

        def xChannel = auto_channel()
        
        echo "channel: " + xChannel

        if (xChannel.equals(util.AutoChannel.TESTING)) 
        {
            echo "testing"
        } 
        else if (xChannel.equals(util.AutoChannel.STABLE)) 
        {
            echo "stable"
        } 
        else 
        {
            echo "other"
        }
    }
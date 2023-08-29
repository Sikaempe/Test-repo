@Library(["vpp", "bop","bop-release"]) _

    stage('TestGit') 
    {
    
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
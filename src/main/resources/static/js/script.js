let arrHidden=[];
setInterval(hiddenElemListRight, 8000);
function hiddenElemListLeft(){
    let i=0;
    while(document.getElementById("banner"+i)!=null){
        arrHidden.push("banner"+i);
        i++;
    }
    if (arrHidden.length==0){

    }
    if (arrHidden.length==1){
        document.getElementById(arrHidden[0]).style.display='flex'
    }
    if (arrHidden.length>1){
        for(let j=0;j<arrHidden.length;j++){
            if(document.getElementById(arrHidden[j]).style.display!='none'){
                document.getElementById(arrHidden[j]).style.display='none'
                if (j==0){
                    document.getElementById(arrHidden[arrHidden.length-1]).style.display='flex'
                    break;
                }
                else{
                    document.getElementById(arrHidden[j-1]).style.display='flex'
                    break;
                }
            }
        }
    }
}

function hiddenElemListRight(){
        let i=0;
        while(document.getElementById("banner"+i)!=null){
            arrHidden.push("banner"+i);
            i++;
        }
        if (arrHidden.length==0){

        }
        if (arrHidden.length==1){
            document.getElementById(arrHidden[0]).style.display='flex'
        }
        if (arrHidden.length>1){
            for(let j=0;j<arrHidden.length;j++){
                if(document.getElementById(arrHidden[j]).style.display!='none'){
                    document.getElementById(arrHidden[j]).style.display='none'
                    if (j==(arrHidden.length-1)){
                        document.getElementById(arrHidden[0]).style.display='flex'
                        break;
                    }
                    else{
                        document.getElementById(arrHidden[j+1]).style.display='flex'
                        break;
                    }
                }
            }
        }
}
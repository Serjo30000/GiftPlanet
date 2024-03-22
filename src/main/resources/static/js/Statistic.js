
function statisticFunView(myObjectJsonArrayView){
    let countFirst = 0
    let countSecond = 0
    let countThird = 0
    let countFourth = 0
    let countFifth = 0
    let countSixth = 0
    let countSeventh = 0
    for (let i=0;i<myObjectJsonArrayView.length;i++){
        if ((new Date()).getFullYear()==(new Date(myObjectJsonArrayView[i].dateCreate).getFullYear())&&(new Date()).getMonth()==(new Date(myObjectJsonArrayView[i].dateCreate).getMonth())&&(new Date()).getDate()==(new Date(myObjectJsonArrayView[i].dateCreate).getDate())){
            countSeventh++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-1))).getFullYear()==(new Date(myObjectJsonArrayView[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-1))).getMonth()==(new Date(myObjectJsonArrayView[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-1))).getDate()==(new Date(myObjectJsonArrayView[i].dateCreate).getDate())){
            countSixth++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-2))).getFullYear()==(new Date(myObjectJsonArrayView[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-2))).getMonth()==(new Date(myObjectJsonArrayView[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-2))).getDate()==(new Date(myObjectJsonArrayView[i].dateCreate).getDate())){
            countFifth++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-3))).getFullYear()==(new Date(myObjectJsonArrayView[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-3))).getMonth()==(new Date(myObjectJsonArrayView[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-3))).getDate()==(new Date(myObjectJsonArrayView[i].dateCreate).getDate())){
            countFourth++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-4))).getFullYear()==(new Date(myObjectJsonArrayView[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-4))).getMonth()==(new Date(myObjectJsonArrayView[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-4))).getDate()==(new Date(myObjectJsonArrayView[i].dateCreate).getDate())){
            countThird++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-5))).getFullYear()==(new Date(myObjectJsonArrayView[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-5))).getMonth()==(new Date(myObjectJsonArrayView[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-5))).getDate()==(new Date(myObjectJsonArrayView[i].dateCreate).getDate())){
            countSecond++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-6))).getFullYear()==(new Date(myObjectJsonArrayView[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-6))).getMonth()==(new Date(myObjectJsonArrayView[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-6))).getDate()==(new Date(myObjectJsonArrayView[i].dateCreate).getDate())){
            countFirst++;
        }
    }
    var ctx = document.getElementById('statisticView').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['1 день', '2 день', '3 день', '4 день', '5 день', '6 день', '7 день'],
            datasets: [{
                label: 'Статистика просмотров',
                data: [countFirst, countSecond, countThird, countFourth, countFifth, countSixth, countSeventh],
                backgroundColor: [
                    'rgba(216, 27, 96, 0.6)',
                    'rgba(3, 169, 244, 0.6)',
                    'rgba(255, 152, 0, 0.6)',
                    'rgba(29, 233, 182, 0.6)',
                    'rgba(156, 39, 176, 0.6)',
                    'rgba(124, 242, 139, 0.6)',
                    'rgba(84, 110, 122, 0.6)'
                ],
                borderColor: [
                    'rgba(216, 27, 96, 1)',
                    'rgba(3, 169, 244, 1)',
                    'rgba(255, 152, 0, 1)',
                    'rgba(29, 233, 182, 1)',
                    'rgba(156, 39, 176, 1)',
                    'rgba(124, 242, 139, 1)',
                    'rgba(84, 110, 122, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Статистика просмотров',
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                    ticks: {
                        min: 0
                    }
                }]
            }
        }
    });
}

function statisticFunBuy(myObjectJsonArrayBuy){
    let countFirst = 0
    let countSecond = 0
    let countThird = 0
    let countFourth = 0
    let countFifth = 0
    let countSixth = 0
    let countSeventh = 0
    for (let i=0;i<myObjectJsonArrayBuy.length;i++){
        if ((new Date()).getFullYear()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getFullYear())&&(new Date()).getMonth()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getMonth())&&(new Date()).getDate()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getDate())){
            countSeventh++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-1))).getFullYear()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-1))).getMonth()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-1))).getDate()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getDate())){
            countSixth++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-2))).getFullYear()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-2))).getMonth()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-2))).getDate()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getDate())){
            countFifth++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-3))).getFullYear()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-3))).getMonth()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-3))).getDate()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getDate())){
            countFourth++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-4))).getFullYear()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-4))).getMonth()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-4))).getDate()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getDate())){
            countThird++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-5))).getFullYear()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-5))).getMonth()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-5))).getDate()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getDate())){
            countSecond++;
        }
        if ((new Date(new Date().setDate(new Date().getDate()-6))).getFullYear()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getFullYear())&&(new Date(new Date().setDate(new Date().getDate()-6))).getMonth()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getMonth())&&(new Date(new Date().setDate(new Date().getDate()-6))).getDate()==(new Date(myObjectJsonArrayBuy[i].dateCreate).getDate())){
            countFirst++;
        }
    }
    var ctx = document.getElementById('statisticBuy').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['1 день', '2 день', '3 день', '4 день', '5 день', '6 день', '7 день'],
            datasets: [{
                label: 'Статистика покупок',
                data: [countFirst, countSecond, countThird, countFourth, countFifth, countSixth, countSeventh],
                backgroundColor: [
                    'rgba(216, 27, 96, 0.6)',
                    'rgba(3, 169, 244, 0.6)',
                    'rgba(255, 152, 0, 0.6)',
                    'rgba(29, 233, 182, 0.6)',
                    'rgba(156, 39, 176, 0.6)',
                    'rgba(124, 242, 139, 0.6)',
                    'rgba(84, 110, 122, 0.6)'
                ],
                borderColor: [
                    'rgba(216, 27, 96, 1)',
                    'rgba(3, 169, 244, 1)',
                    'rgba(255, 152, 0, 1)',
                    'rgba(29, 233, 182, 1)',
                    'rgba(156, 39, 176, 1)',
                    'rgba(124, 242, 139, 1)',
                    'rgba(84, 110, 122, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Статистика покупок',
                position: 'top',
                fontSize: 16,
                padding: 20
            },
            scales: {
                yAxes: [{
                    ticks: {
                        min: 0
                    }
                }]
            }
        }
    });
}
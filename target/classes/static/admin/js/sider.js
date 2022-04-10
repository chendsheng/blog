let curWwwPath=window.document.location.href;
let pathName=window.document.location.pathname;
let pos=curWwwPath.indexOf(pathName);
let localhostPath=curWwwPath.substring(0,pos);
let realPath = localhostPath + $('#realPath').attr('href')

let getSelected = function(checkbox){
    let selected = new Array()
    if (checkbox.length>0) {
        let i = 0
        checkbox.each((index, element) => {
            if ($(element).prop('checked')) {
                selected[i] = $(element).attr('id')
                i++
            }
        })
    }
    return  selected
}

$(function () {
    let side = $('.side')
    $(window).scroll(function () {
        side.css('top', window.scrollY + 'px')
    })

    $(window).resize(function () {
            let width = window.innerWidth
            if (width>1200){
                side.css({
                    'width': '180px',
                    'min-width': '180px',
                    'overflow': 'auto',
                })
                $('.side span').css('display','inline')
                $('.side>.d-flex').css('margin-top','0')
                $('.side>div:first-child').css('margin-top','0')
            }else if (width>768){
                side.css({
                    'width': '0',
                    'min-width': '70px',
                    'overflow': 'auto'
                })
                $('.side span').css('display','none')
                $('.side>.d-flex').css('margin-top','30px')
                $('.side>div:first-child').css('margin-top','30px')
            }else {
                side.css({
                    'width': '0',
                    'min-width': '0',
                    'overflow': 'auto'
                })
            }
    }
    )

    $('.header>.menu>img').click(function () {
        if (side.outerWidth()>100 || (window.outerWidth<768 && side.outerWidth()<10)){
            side.css({
                'width': '0',
                'min-width': '70px',
                'overflow': 'auto'
            })
            $('.side span').css('display','none')
            $('.side>.d-flex').css('margin-top','30px')
            $('.side>div:first-child').css('margin-top','30px')
        }else if (side.outerWidth()>0 && window.outerWidth<768){
            side.css({
                    'width': '0',
                    'min-width': '0',
                    'overflow': 'auto'
                })
        } else{
            side.css({
                'width': '180px',
                'min-width': '180px',
                'overflow': 'auto',
            })
            $('.side span').css('display','inline')
            $('.side>.d-flex').css('margin-top','0')
            $('.side>div:first-child').css('margin-top','0')
        }
    })

    $('.header>.menu>span').click(function () {
        window.location.href = realPath + '/admin'
    })
})
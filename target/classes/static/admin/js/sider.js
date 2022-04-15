//公共方法和变量
let curWwwPath = window.document.location.href;
let pathName = window.document.location.pathname;
let pos = curWwwPath.indexOf(pathName);
let localhostPath = curWwwPath.substring(0, pos);
let realPath = localhostPath + $('#realPath').attr('href');

//被选中的复选框
let getSelected = function (checkbox) {
    let selected = new Array()
    if (checkbox.length > 0) {
        let i = 0;
        checkbox.each((index, element) => {
            if ($(element).prop('checked')) {
                selected[i] = $(element).attr('id');
                i++;
            }
        })
    }
    return selected;
}

//是否被选中
let isSelectd = function (e) {
    let input_check = $(this).find('input');
    if (e.target != input_check[0]) {
        input_check.prop('checked', !input_check.prop('checked'));
    }
    if ($(this).attr('id') === 'all') {
        $('input').prop('checked', input_check.prop('checked'));
    }
    if (!input_check.prop('checked')) {
        $('#all').find('input').prop('checked', false)
    }
}

let goTop = function () {
    document.scrollingElement.scrollTop = 0;
    document.body.scrollTop = 0;
}

$(function () {
        let side = $('.side');
        let main = $('.main');

        let side_resize = function () {
            if (side.outerWidth() > 100) {
                side.css({
                    'max-width': '70px',
                    'min-width': '70px'
                })
                $('.side span').css('display', 'none');
                $('.side>.d-flex').css('margin-top', '30px');
                $('.side>div:first-child').css('margin-top', '30px');
                main.css('margin-left', '70px');
            } else {
                side.css({
                    'width': '180px',
                    'min-width': '180px'
                })
                $('.side span').css('display', 'inline');
                $('.side>.d-flex').css('margin-top', '0');
                $('.side>div:first-child').css('margin-top', '0');
                main.css('margin-left', '180px')
            }
        }

        $('.header>.menu>img').click(side_resize)

        $('.header>.menu>span').click(function () {
            window.location.href = realPath + '/admin';
        })
    }
)
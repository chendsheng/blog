$(function () {
    let curWwwPath = window.document.location.href;
    let pathName = window.document.location.pathname;
    let pos = curWwwPath.indexOf(pathName);
    let localhostPath = curWwwPath.substring(0, pos);
    let realPath = localhostPath + $('#realPath').attr('href');

    let show = function (count, currentIndex) {
        $('.pager ul').show();
        $('pager ul button').parent().show();
        //页码显示
        if (count <= 1) {
            $('.pager ul').hide()
        } else if (count <= 9) {
            for (let i = 0; i < count; i++) {
                $('.num>button').eq(i).text(i + 1)
            }
            $('.num>button').each(function (index) {
                if (index >= count) {
                    $(this).parent().hide()
                }
            })
        } else {
            let backNum = count - currentIndex;
            if (currentIndex >= 5 && backNum >= 5) {
                $('.num button').each((i, ele) => {
                    if (i == 1 || i == 7) {
                        $(ele).html("<strong>...</strong>")
                    } else if (i == 0) {
                        $(ele).text(i + 1)
                    } else if (i == 8) {
                        $(ele).text(count)
                    } else {
                        $(ele).text(i + currentIndex - 4)
                    }
                })
            } else if (currentIndex >= 5 && backNum < 5) {
                $('.num button').each((i, ele) => {
                    if (i == 1) {
                        $(ele).html("<strong>...</strong>")
                    } else if (i == 0) {
                        $(ele).text(i + 1)
                    } else {
                        $(ele).text(count - 8 + i)
                    }
                })
            } else {
                $('.num button').each((i, ele) => {
                    if (i == 7) {
                        $(ele).html("<strong>...</strong>")
                    } else if (i == 8) {
                        $(ele).text(count)
                    } else {
                        $(ele).text(i + 1)
                    }
                })
            }
        }

        $('.num').each((i, ele) => {
            if ($(ele).text().trim() == currentIndex) {
                $(ele).addClass('meactive').siblings().removeClass('meactive')
            } else if ($(ele).text().trim == "") {
                $(ele).parent().html("...")
            }
        })

        $('.pages>li:first,.pages>li:last').show();
        if (currentIndex == 1) {
            $('.pages>li:first').hide()
        }
        if (currentIndex == count) {
            $('.pages>li:last').hide()
        }
    }
    let currentIndex = 1;
    let search = $('#search');

    //搜素
    function go() {
        if (search.val().trim().length > 0) {
            currentIndex = 1;
            $('#blogsList').load(realPath + '/list?title=' + search.val().trim(),
                function () {
                    show($('.blog-wrapper').attr("count"), 1)
                })
        }
    }

    //翻页后回到顶部
    function top() {
        document.scrollingElement.scrollTop = 0;
        document.body.scrollTop = 0;
    }

    $('.search>button').click(go)
    $('.search>input').focus().keydown(function (e) {
        if (e.keyCode == 13) {
            go()
        }
    })
    $('.search>input').blur();
    show($('.blog-wrapper').attr("count"), currentIndex);

    //直接跳转页面
    $('.num>button').click(function () {
        if ($(this).text().trim() != "") {
            currentIndex = parseInt($(this).text().trim());
            show($('.blog-wrapper').attr("count"), currentIndex);
            $('#blogsList').load(realPath + '/list?pageNum=' + $(this).text().trim() + '&' + $('.blog-wrapper').attr("selectType") + '=' + $('.blog-wrapper').attr("selectValue"), top)
        }
    })

    //上一页
    $('#previous').click(function () {
        currentIndex -= 1;
        show($('.blog-wrapper').attr("count"), currentIndex);
        $('#blogsList').load(realPath + '/list?pageNum=' + currentIndex + '&' + $('.blog-wrapper').attr("selectType") + '=' + $('.blog-wrapper').attr("selectValue"), top)
    })

    //下一页
    $('#next').click(function () {
        currentIndex += 1;
        show($('.blog-wrapper').attr("count"), currentIndex);
        $('#blogsList').load(realPath + '/list?pageNum=' + currentIndex + '&' + $('.blog-wrapper').attr("selectType") + '=' + $('.blog-wrapper').attr("selectValue"), top)
    })

    //按分类搜索
    $('.category-wrapper>a').click(function () {
        currentIndex = 1;
        $('#blogsList').load(realPath + '/list?categoryId=' + $(this).attr('categoryId'),
            function () {
                show($('.blog-wrapper').attr("count"), currentIndex);
                top()
            })
    })

    //按标签搜索
    $('.tag-wrapper>a').click(function () {
        currentIndex = 1;
        $('#blogsList').load(realPath + '/list?tagId=' + $(this).attr('tagId'),
            function () {
                show($('.blog-wrapper').attr("count"), currentIndex);
                top()
            })
    })

    //最新文章搜索
    $('.recent-wrapper>.recent-item').click(function () {
        window.open(realPath + '/detail/' + $(this).attr('articleId'))
    })

    //文章详情
    $('#blogsList').on('click', ".blog-item", function () {
        window.open(realPath + '/detail/' + $(this).attr('blogId'))
    })
})
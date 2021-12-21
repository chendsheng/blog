$(function () {
    let curWwwPath=window.document.location.href;
    let pathName=window.document.location.pathname;
    let pos=curWwwPath.indexOf(pathName);
    let localhostPath=curWwwPath.substring(0,pos);
    let realPath = localhostPath + $('#realPath').attr('href')

    let myEditor = editormd.markdownToHTML("md",{
        htmlDebode: true,
        width: "90%",
        path: realPath+ '/editor/lib/',
        emoji: true,
        taskList        : true,
        tex             : true,  // 默认不解析
        flowChart       : true,  // 默认不解析
        sequenceDiagram : true,  // 默认不解析

    })

    //当前分类点击跳转
    $('#category').click(function () {
        window.location.href = realPath+"?categoryId="+$(this).attr('categoryId')
    })
    //当前标签跳转
    $('.message .tag').click(function () {
        window.location.href = realPath+"?tagId="+$(this).attr('tagId')
    })

    //home标志跳转
    $('.path>.home').click(function () {
        window.location.href = realPath +'?title'
    })

    //input搜素
    function go() {
        search = $('.search>input').val()
        window.location.href = realPath+'?title='+search.trim()
    }
    $('.search>button').click(go)
    $('.search>input').focus().keydown(function (e) {
        if (e.keyCode==13){
            go()
        }
    })
    $('.search>input').blur()

    //按分类搜索
    $('.category-wrapper>a').click(function () {
       window.location.href=realPath+'?categoryId='+ $(this).attr('categoryId')
    })

    //按标签搜索
    $('.tag-wrapper>a').click(function () {
       window.location.href = realPath+'?tagId='+ $(this).attr('tagId')
    })
})
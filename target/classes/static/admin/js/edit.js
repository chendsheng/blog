$(function () {
    $('#tags').tagsInput({
        width: '100%',
        height: '38px',
        defaultText: '文章标签'
    })

    let editor = editormd("test-editor", {
        width: "100%",
        height: "650px",
        path: realPath+'/editormd/lib/',
        codeFold: true,

        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp","ico"],
        imageUploadURL : realPath+"/admin/upload/editormdPic",

        //syncScrolling : false,
        saveHTMLToTextarea: true,    // 保存 HTML 到 Textarea
        searchReplace: true,
        //watch : false,                // 关闭实时预览
        htmlDecode: "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        //toolbar  : false,             //关闭工具栏
        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
        emoji: true,
        taskList: true,
        tocm: true,         // Using [TOCM]
        tex: true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart: true,             // 开启流程图支持，默认关闭
        sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
        //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
        // imageUpload: true,
        // imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp", "ico"],
        // imageUploadURL: "./php/upload.php",
    })

    let pic = $('#pic')
    let randomPic = $('#randomPic')
    let id = $('input[name="id"]').eq(0).val()
    pic.change(function () {
        if ($(this).val() != "") {
            randomPic.attr("disabled",true)
        }else {
            randomPic.attr("disabled",false)
        }
    })

    let url = $('#form').attr('action')

    $('#confirm').click(function () {
        if ($('#category').val()==null || $('#title').val().trim()=="" ||
            $('#summary').val().trim()=="" ||$('#tags').val().trim()==""||
            $('#content').val().trim()=="" || (id ==""?(pic.val()=="" && !randomPic.prop('checked')):false)){
            myalert("提示框","文章必填不可为空")
        }else {
            let formData = new FormData($('#form')[0])
            formData.append("pic",pic[0].files[0])
            if (!randomPic.attr("disabled")){
                formData.append("randomPic",randomPic.prop('checked'))
            }
            $.ajax({
                url: url,
                type: 'post',
                contentType:false,
                processData:false,
                data: formData,
                success:function(result){
                    if (result.code==200){
                        myalert("提示框",result.message,function () {
                            window.location.href = realPath + "/admin/edit"
                        })
                    }else {
                        myalert("提示框",result.message)
                    }

                }
            })
        }
    })
})
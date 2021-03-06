$(function () {
    //全选或取消
    $('#updateArticle').on('click', 'tr', isSelectd);

    //更新
    $('#update').click(function () {
        let selected = getSelected($('input[name="checkbox"]'));
        if (selected.length != 1) {
            myalert("提示框", "请选择一个要修改的文章");
        } else {
            window.location.href = realPath + '/admin/article/' + selected[0];
        }
    })

//删除
    $(".operation").on('click', '#delete', function () {
        let selected = getSelected($('input[name="checkbox"]'));
        if (selected.length > 0) {
            $.ajax({
                url: realPath + "/admin/articles",
                type: 'delete',
                data: {
                    'ids': selected
                },
                success: function (result) {
                    if (result.code == 200) {
                        myalert("提示框", result.message, function () {
                            $('#updateArticle').load(realPath + '/admin/article/list?pageSize=' + $('#pageSize').val() + '&search=' + $('#search').val());
                        })
                    } else {
                        myalert("提示框", result.message);
                    }
                }
            })
        } else {
            myalert("提示框", "请选择要删除的文章");
        }
    })

//每页记录数
    $('#updateArticle').on('change', '#pageSize', function (e) {
        $('#updateArticle').load(realPath + '/admin/article/list?pageSize=' + $(this).val() + '&search=' + $('#search').val(), goTop);
    })

//翻页跳转
    $('#updateArticle').on('click', '.page a', function (e) {
        e.preventDefault();
        $('#updateArticle').load(localhostPath + $(this).attr('href'), goTop);
    })

//搜索
    $("#submit").click(function () {
        $('#updateArticle').load(realPath + '/admin/article/list?search=' + $('#search').val() + '&pageSize=' + $('#pageSize').val());
    })
})
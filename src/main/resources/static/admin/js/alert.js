(function ($) {
    $.alerts = {
        alert: function (title, message, callback) {
            if (title == null) title = 'Alert';
            $.alerts._show(title, message, null, 'alert', function (result) {
                if (callback) callback(result);
            });
        },

        confirm: function (title, message, callback) {
            if (title == null) title = 'Confirm';
            $.alerts._show(title, message, null, 'confirm', function (result) {
                if (callback) callback(result);
            });
        },


        _show: function (title, msg, value, type, callback) {
            var _html = "";
            _html += '<div class="pop-mask"></div>';
            _html += '<div class="pop" id="pop" style="width: 320px;">';
            _html += '<div class="pop-header clearfix">';
            _html += '<div class="title fl">' + title + '</div>';
            _html += '<a class="pop-close fr" href="javascript:;"></a>';
            _html += '</div>';
            _html += '<div class="pop-main">';
            _html += '<div class="csn-tip">' + msg + '</div>';
            _html += '</div>';
            _html += '<div class="pop-footer">';
            if (type == "alert") {
                _html += '<a class="pop-blue" href="javascript:;">确定</a>';
            }
            if (type == "confirm") {
                _html += '<a class="pop-blue" href="javascript:;">确定</a>';
                _html += '<a class="pop-gray" href="javascript:;">取消</a>';
            }
            _html += '</div>';
            _html += '</div>';

            //必须先将_html添加到body，再设置Css样式
            $("body").append(_html); GenerateCss();
            switch (type) {
                case 'alert':
                    $(".pop-blue,.pop-close").click(function () {
                        $.alerts._hide();
                        callback(true);
                    });
                    $(".pop-blue").focus().keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) $(".pop-blue").trigger('click');
                    });
                    break;
                case 'confirm':
                    $(".pop-blue").click(function () {
                        $.alerts._hide();
                        if (callback) callback(true);
                    });
                    $(".pop-gray,.pop-close").click(function () {
                        $.alerts._hide();
                        if (callback) callback(false);
                    });
                    $(".pop-blue").focus().keypress(function (e) {
                        if (e.keyCode == 13) $(".pop-blue").trigger('click');
                        if (e.keyCode == 27) $(".pop-gray").trigger('click');
                    });
                    break;

            }
        },
        _hide: function () {
            $(".pop-mask,#pop").remove();
        }
    }
    // Shortuct functions
    myalert = function (title, message, callback) {
        $.alerts.alert(title, message, callback);
    }

    myconfirm = function (title, message, callback) {
        $.alerts.confirm(title, message, callback);
    };



    //生成Css
    var GenerateCss = function () {
        $(".pop").css({
            'font-size': '14px', color: '#333', 'font-family': 'microsoft yahei',
        });
        $(".pop .clearfix").css({
            zoom: '1',
        });
        $(".pop a").css({
            outline: 'none', 'text-decoration': 'none',
        });
        $(".fr").css({
            'float': 'right',
        });
        $(".fl").css({
            'float': 'left',
        });

        $(".pop-mask").css({
            position: 'fixed', top: '0px', left: '0px', width: '100%', height: '100%', background: '#a8abaf', opacity: '0.5', filter: 'alpha(opacity=50)', 'z-index': '10000',
        });

        $(".pop").css({
            position: 'fixed', background: '#fff', 'z-index': '10000',
        });

        $(".pop-header").css({
            height: '40px', 'line-height': '40px', background: '#1bbc9d', color: '#fff',
        });

        $(".pop .title").css({
            'padding-left': '20px',
        });

        $(".pop-close").css({
            width: '40px', height: '40px', background: 'url(/icon/pop_close.png) no-repeat center',
        });
        $(".pop-main").css({
            padding: '20px',
        });
        $(".csn-tip").css({
            height: '100px', 'line-height': '100px', 'text-align': 'center', color: '#666', overflow: 'hidden',
        });
        $(".pop-footer").css({
            height: '52px', background: '#f3f3f3', 'text-align': 'center', 'font-size': '0px',
        });
        $(".pop-footer a").css({
            display: 'inline-block', display: 'inline-block', margin: '10px 10px 0px', width: '70px', height: '28px', 'line-height': '28px', 'text-align': 'center', 'font-size': '14px', 'border-radius': '2px',
        });
        $(".pop-blue").css({
            background: '#1bbc9d', border: '1px solid #1bbc9d', color: '#fff',
        });

        $(".pop-blue:hover").css({ background: '#1bbc9d', border: '1px solid #1bbc9d', });
        $(".pop-gray").css({ background: '#fff', border: '1px solid #dcdcdc', color: '#333', });
        $(".pop-gray:hover").css({ background: '#f9f9f9', border: '1px solid #dcdcdc', color: '#666', });

        var _widht = document.documentElement.clientWidth; //屏幕宽
        var _height = document.documentElement.clientHeight; //屏幕高

        var boxWidth = $("#pop").width();
        var boxHeight = $("#pop").height();
        //让提示框居中
        $("#pop").css({ top: (_height - boxHeight) / 2 + "px", left: (_widht - boxWidth) / 2 + "px" });
    }

})(jQuery)
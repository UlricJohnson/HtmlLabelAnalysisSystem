<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>首页ftl</title>

    <#-- 不用设置相对路径或项目路径 -->
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/css/index.css">

    <script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/index.js"></script>
</head>
<body>
    <div class="layui-layout layui-layout-admin">
    <#-- 头部区域 -->
    <div class="layui-header">
        <div class="layui-logo" style="font-weight: bold">
            菜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单
        </div>

        <#-- 在这里输入起始 URL 和计划爬取的网页数 -->
        <form id="dataForm" class="layui-form" action="/user/findUserByUsername" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">输入框</label>
                <div class="layui-input-block">
                    <input class="layui-input" name="username" placeholder="请输入网页链接"
                           style="width: 350px;" type="text" required lay-verify="required" autocomplete="off">
                </div>
            </div>

            <#-- 按钮：提交和重置 -->
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="formDemo">搜索</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>

        <#-- 用户头像信息 -->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    Ulric
                </a>
            </li>
        </ul>
    </div>

    <#-- 左侧导航区域 -->
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">所有商品</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">列表一</a></dd>
                        <dd><a href="javascript:;">列表二</a></dd>
                        <dd><a href="javascript:;">列表三</a></dd>
                        <dd><a href="javascript:;">超链接</a></dd>
                    </dl>
                </li>
            <#--<li class="layui-nav-item"><a href="">云市场</a></li>-->
            </ul>
        </div>
    </div>

    <!-- 内容主体区域 -->
    <div class="layui-body">
        <div style="padding: 15px;">
            <h2>搜索结果：</h2>
            <div id="result">${result}</div>
        </div>
    </div>
</div>

    <#-- JavaScript代码区域 -->
    <script>
        layui.use('element', function () {
            var element = layui.element;
        });
    </script>
</body>
</html>
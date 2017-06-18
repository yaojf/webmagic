<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->

</head>
<body>


<table class="table table-bordered">
    <thead>
    <tr>
        <th>id</th>
        <th>time_dimension(1:周 2:月 3:季)</th>
        <th>time_value(时间值)</th>
        <th>type_id(应用分类)</th>
        <th>type_name(应用分类名称)</th>
        <th>rank(排名)</th>
        <th>app_name(应用名称)</th>
        <th>coverage(覆盖率)</th>
        <th>active_rate(活跃率)</th>
        <th>create_time</th>
        <th>create_person</th>
        <th>update_time</th>
        <th>update_person</th>
    </tr>
    </thead>
    <tbody>
    <#list appRankList as appRank>
    <tr>
        <th scope="row">${appRank.id}</th>
        <td>${appRank.timeDimension}</td>
        <td>${appRank.timeValue}</td>
        <td>${appRank.typeId}</td>
        <td>${appRank.typeName}</td>
        <td>${appRank.rank}</td>
        <td>${appRank.appName}</td>
        <td>${appRank.coverage}</td>
        <td>${appRank.activeRate}</td>
        <td>${appRank.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
        <td>${appRank.createPerson}</td>
        <td>${appRank.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
        <td>${appRank.updatePerson}</td>
    </tr>
    </#list>

    </tbody>
</table>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/static/js/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>
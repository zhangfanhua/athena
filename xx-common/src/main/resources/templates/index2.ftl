<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>EXCEL头部信息生成</title>
    <meta name="keywords" content="excel 生成">

    <#import "common/common-import.ftl" as netCommon>
    <#import "code-generator/util/select.ftl" as items/>
    <@netCommon.commonStyle />
    <@netCommon.commonScript />

<script>

    $(function () {
        /**
         * 初始化 table sql 3
         */
        var fromData = CodeMirror.fromTextArea(document.getElementById("fromData"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-sql",
            lineWrapping:false,
            readOnly:false,
            foldGutter: true,
            //keyMap:"sublime",
            gutters:["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        fromData.setSize('auto','auto');
        // controller_ide
        var genCodeArea = CodeMirror.fromTextArea(document.getElementById("genCodeArea"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping:true,
            readOnly:false,
            foldGutter: true,
            //keyMap:"sublime",
            gutters:["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        genCodeArea.setSize('auto','auto');

        var codeData;
        // 使用：var jsonObj = $("#formId").serializeObject();
        $.fn.serializeObject = function()
        {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };

        /**
         * 获取对象
         */
        $('#gainTable').click(function ()  {
            var dataType = $("#dataType").val();
            var jsonData;
            if(dataType == 'post'){
                jsonData = {
                    "dataType":dataType,
                    "customUrl":$("#customUrl").val(),
                    "tableSql": fromData.getValue()
                };
            }else{
                jsonData = {
                    "dataType":dataType,
                    "customUrl":$("#customUrl").val()
                };
            }

            $.ajax({
                type: 'POST',
                url: base_url + "/gainTable",
                data:(JSON.stringify(jsonData)),
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    if (data.code === 200) {
                        codeData = JSON.parse(data.data.table);
                        var tr;
                        for (var i = 0; i < codeData.length; i++) {
                            tr += "<tr id='t"+codeData[i]+"'>"+
                                "<td>"+(i+1)+"</td>"+
                                "<td>"+codeData[i]+"</td>"+
                            "<td><input class='easyui-textbox' name='subject'></td>"+
                            "<td><input class='easyui-textbox' name='subject'></td></tr>";

                        }
                        $("#columnTable").append(tr);
                        $.toast("√ 获取对象成功");
                    } else {
                        $.toast("× 获取对象失败 :"+data.msg);
                    }
                }
            });
            return false;
        });
        $('#btnGenCode').click(function ()  {
            var arrayObj = new Array();
            $('#columnTable tr').each(function(i){
                if(i>0){
                    var str1= {};
                    $(this).children('td').each(function(j){
                        if(j==1){
                            str1['titleKey']=$(this).text();
                        }
                        if(j==2){
                            str1['titleVal']=$(this).find("input").val();
                        }
                        if(j==3){
                            str1['id']=$(this).find("input").val();
                        }
                    });
                    arrayObj.push(str1);
                }
            });
            genCodeArea.setValue(JSON.stringify(arrayObj));
            genCodeArea.setSize('auto', 'auto');
            return false;
        });
    });
</script>
</head>
<body style="background-color: #e9ecef">

    <div class="container">
        <nav class="navbar navbar-dark bg-primary btn-lg">
            <a class="navbar-brand">EXCEL生成</a>
        </nav>
    </div>

<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">
        <h2>EXCEL头部信息</h2>
        <p class="lead">头部信息填写并排序<br>
        </p>
        <div id="donate" class="container" show="no"></div>
        <hr>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">URL:</span>
            </div>
            <select type="text" class="form-control" id="dataType" name="dataType">
                <option value="get">GET</option>
                <option value="post">POST</option>
            </select>
            <input type="text" class="form-control" id="customUrl" name="customUrl" placeholder="请输入URL" value="">

        </div>
        <div class="input-group mb-3">
            <textarea id="fromData" placeholder="请输入JSON ..." class="form-control btn-lg" style="height: 250px;"></textarea>
        </div>
        <div class="input-group mb-3">
            <button class="btn btn-primary btn-lg disabled" id="gainTable" role="button" data-toggle="popover" data-content="">获取对象 »</button>
        </div>
        <div id="columnDiv" class="input-group mb-3">
            <table id="columnTable" style="width: 1000px;height: auto" cellpadding="1" cellspacing="1">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>返回字段名</th>
                    <th>对应中文</th>
                    <th>对应排序</th>
                </tr>
                </thead>
            </table>
        </div>
        <hr>
        <div class="input-group mb-3">
            <button class="btn btn-primary btn-lg disabled" id="btnGenCode" role="button" data-toggle="popover" data-content="">开始生成 »</button>
        </div>

        <hr>
        <textarea id="genCodeArea" class="form-control btn-lg" ></textarea>
    </div>
</div>

</body>
</html>

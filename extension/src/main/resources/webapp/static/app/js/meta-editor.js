$(".hflist .box-header").each(function(){
    var $header = $(this);
    var $h2 = $header.find("h2:first");
    $header.next().next().prepend($h2);
    $header.remove();
});

$("[id='dataset_meta_xeditor\#dataset.rule.field_list']").hide();
$("[id='dataset_meta_xeditor\#dataset.hid.field_list']").hide();

// $("td.center:visible").html('<a class="btn btn-success" href="javascript:void(0)" ><i class=" icon-file-alt"></i></a>');

// $("thead:visible").each(function(){$(this).find("th:last").css("width", "5%");});

$("[dc=SYSTEM_EMPTY_DATASET]").remove();
$(".hfcontainer").css("margin-left","0px");


$("button").after($('<button id="goback" class="btn btn-primary" onclick="javascript:void(0)"  title=" 返 回  " style="margin-left: 20px;"> 返 回 </button>'));

$("#goback").click(function () {
    var href = window.location.href;
   var referCode = href.substring(href.indexOf("&r_code=") + "&r_code=".length);
    var referParam = href.substring(href.indexOf("&r_param=") + "&r_param=".length);

    window.location.href = "/runtime/" +
        referCode.substring(0, referCode.indexOf("&")) + ".html?" +
        referParam.substring(0, referParam.indexOf("&")) + "&isPop=true";

});
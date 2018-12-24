(function( angular, $) {


    angular.module('myApp').controller("APICtrl",['$scope','$http','$state','Upload',function($scope,$http,$state,Upload) {
        $scope.APIdata=null;
        $scope.fileName=null;
        $scope.upload = function () {
            console.log($scope.paramsList);
            Upload.upload(
                {
                    url: 'upload/image',
                    params:{
                        'guid':$scope.paramsList[0].guid,
                        'user':$scope.paramsList[0].user
                    },
                    file: $scope.paramsList[0].file
                })
                .progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                })
                .success(function (data, status, headers, config) {
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                    $scope.APIdata=data;
                    $scope.num=$scope.APIdata.id;
                    $scope.department=$scope.APIdata.department;
                    $scope.name=$scope.APIdata.name;
                    $scope.other=$scope.APIdata.number;
                })
                .error(function (data, status, headers, config) {
                    console.log('error status: ' + status);
                })
        };

        // $scope.$watch('file', function (file) {
        //     if(file!=null)
        //         $scope.upload($scope.file);
        // });

        $scope.reader = new FileReader();
        $scope.thumb=[];
        $scope.fileList=[];
        $scope.paramsList=[];
        $scope.thumb_default="../images/add.png";
        $scope.myImage='';
        $scope.myCroppedImage='';
        $scope.uploadFiles=function(file) {
            console.log(file);
            var guid = (new Date()).valueOf();
            $scope.reader.readAsDataURL(file);
            $scope.reader.onload = function (ev) {
                $scope.$apply(function () {
                    $scope.thumb.push({
                        'guid': guid,
                        'imgSrc': ev.target.result
                    });
                    $scope.myImage=ev.target.result;
                    $scope.paramsList.push({
                        'guid': guid,
                        'user': 1,
                        'file': file
                    })
                    $scope.fileName=file.name;
                });
            };

        };

        $scope.img_del=function(key){
           $scope.thumb.splice(key,1);
           $scope.paramsList.splice(key,1);
        };
// Grab elements, create settings, etc.
        $scope.MediaHandle=function() {
            var canvas = document.getElementById("canvas"),
                context = canvas.getContext("2d"),
                video = document.getElementById("video"),
                videoObj = {"video": true},
                errBack = function (error) {
                    console.log("Video capture error: ", error.code);
                };
// Put video listeners into place
            navigator.mediaDevices.getUserMedia({audio: true, video: true})
                .then(function (stream) {
                    var video = document.querySelector('video');
                    // 旧的浏览器可能没有srcObject
                    if ("srcObject" in video) {
                        video.srcObject = stream;
                    } else {
                        // 防止在新的浏览器里使用它，应为它已经不再支持了
                        video.src = window.URL.createObjectURL(stream);
                    }
                    video.onloadedmetadata = function (e) {
                        video.play();
                    };
                })
                .catch(function (err) {
                    console.log(err.name + ": " + err.message);
                });
            if (navigator.getUserMedia) { // Standard
                navigator.getUserMedia(videoObj, function (stream) {
                    video.srcObject = stream;
                    video.play();
                }, errBack);
            } else if (navigator.webkitGetUserMedia) { // WebKit-prefixed
                navigator.webkitGetUserMedia(videoObj, function (stream) {
                    video.srcObject = window.webkitURL.createObjectURL(stream);
                    video.play();
                }, errBack);
            } else if (navigator.mozGetUserMedia) { // Firefox-prefixed
                navigator.mozGetUserMedia(videoObj, function (stream) {
                    video.srcObject = window.URL.createObjectURL(stream);
                    video.play();
                }, errBack);
            }


           $scope.snapOK=function(){
                    context.drawImage(video, 0, 0, 640, 480);
                    var guid = (new Date()).valueOf();
                    $scope.thumb.push({
                   'guid': guid,
                   'imgSrc': canvas.toDataURL(),
                     });
                     $scope.paramsList.push({
                   'guid': guid,
                   'user': 1,
                   'file': $scope.dataURLtoFile(canvas.toDataURL(),guid),
                    });

           };
        };

$scope.scale=1;
$scope.resultscale=1;


$scope.clickHandler=function(){
    var width=null;
    var height=null;
    var x=null;
    var y=null;
    var jcrop_holder=null;
    var imgURL=null;
    var jcrop_api;
    var previewCanvas = document.getElementById("previewCanvas");
    var img=document.getElementById("target");
    var realWidth=0;
    var realHeight=0;
    var imgObj = new Image();
    imgObj.onload=function (ev) { realWidth=imgObj.width;
        realHeight=imgObj.height;}
    imgObj.src = URL.createObjectURL($scope.paramsList[0].file);

    var previewCanvasPen=null;

    if(previewCanvas.getContext){
        previewCanvasPen = previewCanvas.getContext("2d");
    }
    previewCanvasPen.clearRect(0,0,temp1*0.8,temp1*0.8*0.7);
    previewCanvasPen.restore();

    var getPixelRatio = function(context) {
        var backingStore = context.backingStorePixelRatio ||
            context.webkitBackingStorePixelRatio ||
            context.mozBackingStorePixelRatio ||
            context.msBackingStorePixelRatio ||
            context.oBackingStorePixelRatio ||
            context.backingStorePixelRatio || 1;

        return (window.devicePixelRatio || 1) / backingStore;
    };

    var ratio = getPixelRatio(previewCanvasPen);

    if (jcropObject) {
        jcropObject.destroy();
    }
    var temp1=0;
    var temp2 =0;
    var jcropObject=null;



        jQuery(function($) {
            // 创建变量(在这个生命周期)的API和图像大小
            var jcrop_api = null, boundx, boundy,

                // 获取预览窗格相关信息

                $preview = $('#preview-pane'),
                $pcnt = $('#preview-pane .preview-container'),
                $pimg = $('#preview-pane .preview-container img'),
                xsize = $pcnt.width(), ysize = $pcnt.height();
            var a =document.body;
            if(a.clientWidth>=800){
                temp1=800*0.9;
                temp2=temp1*0.7;
                previewCanvas.style.cssText='width: '+temp1*0.8+'px;height: '+temp1*0.8*0.7+'px;margin-left: 10%;margin-right: 10%;';

            }
            else if(a.clientWidth<800&&a.clientWidth>=580){
                temp1=a.clientWidth*0.8;
                temp2=temp1*0.7;
                previewCanvas.style.cssText='width: '+temp1*0.8+'px;height: '+temp1*0.8*0.7+'px;margin-left: 10%;margin-right: 10%;';

            }
            else if(a.clientWidth<570){
                temp1=a.clientWidth*0.8;
                temp2=temp1*0.7;
                previewCanvas.style.cssText='width: '+temp1*0.8+'px;height: '+temp1*0.8*0.7+'px;margin-left: 10%;margin-right: 10%;';

            }
            console.log(temp1);
            console.log(temp2);

            $('#target').Jcrop({
                setSelect: [ 20, 20, 150, 150 ],
                // allowResize:false,
                boxWidth:temp1,
                boxHeight:temp2,
                onChange : updatePreview,
                onSelect : updatePreview,
                aspectRatio : xsize / ysize
            }, function() {
                // 使用API来获得真实的图像大小
                var bounds = this.getBounds();
                boundx = bounds[0];
                boundy = bounds[1];
                jcrop_api = this;
                jcropObject=this;
                // 预览进入jcrop容器css定位
                $preview.appendTo(jcrop_api.ui.holder);
            });


            function updatePreview(c) {
                // 设置预览
                if (parseInt(c.w) > 0) {
                    var x1=c.w*0.8;
                    var x2=c.h*0.9;
                    var rx = xsize / x1;
                    var ry = ysize / x2;
                    $pimg.css({
                        width : Math.round(rx * boundx) + 'px',
                        height : Math.round(ry * boundy) + 'px',
                        marginLeft : '-' + Math.round(rx * c.x) + 'px',
                        marginTop : '-' + Math.round(ry * c.y) + 'px',
                    });
                    $pcnt.css({
                        top:temp1+10+'px',
                        right:temp1*0.5+'px',
                        width:temp1*0.5+'px',
                        height:temp1*0.3+'px'
                    })
                }
                // 赋值
                x = c.x;
                y = c.y;
                console.log(c.w);
                console.log(c.h);
                width = c.w;
                height = c.h;

                previewCanvasPen.clearRect(0,0,temp1*0.8,temp1*0.8*0.7);
                if(jcropObject!=null) {


                    if(a.clientWidth>=800){
                        previewCanvasPen.drawImage(img, x, y, width*1.9, height*2.6, 0, 0, temp1*0.8,temp1*0.8*0.7);
                    }
                    else if(a.clientWidth<800&&a.clientWidth>=580){
                        previewCanvasPen.drawImage(img, x, y, width*1.7, height*2.2, 0, 0, temp1*0.8,temp1*0.8*0.7);
                    }
                    else if(a.clientWidth<560){
                        previewCanvasPen.drawImage(img, x, y, width, height, 0, 0, temp1*0.8,temp1*0.8*0.7);

                    }
                    // else if(temp1>=800)
                    //     previewCanvasPen.drawImage(img, x, y, width*1.6, height*2, 0, 0, temp1*0.8,temp1*0.8*0.7);
                }


                // previewCanvasPen.clearRect(0,0,temp1*0.8,temp1*0.8*0.7);
                //
                //
                // previewCanvasPen.drawImage(img , startX , startY , clipWidth , clipHeight , 0 , 0 , temp1*0.8 , temp1*0.8*0.7);
                //  // var scaleX=temp1*0.8/(width-x);
                //  // var scalyY=temp1*0.8*0.7/(height-y);
                //  // previewCanvasPen.scale(scaleX,scalyY);

            };

                    // 获取window的 URL工具
            var URL = window.URL || window.webkitURL;
            // 通过 file生成目标 url
            imgURL = URL.createObjectURL($scope.paramsList[0].file);
                    // 用这个URL产生一个 <img> 将其显示出来
                    if (jcrop_api) {
                        jcrop_api.setImage(imgURL);
                    }
                    $('.jcrop-preview').attr('src', imgURL);

        });
};
        $scope.dataURLtoFile= function (dataurl,filename) {
            var index = filename.lastIndexOf('.');
            filename=filename.substring(0,index)+'.png';


            var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new File([u8arr], filename, {type: mime});
        };
        $scope.cutOk=function(){
            var previewCanvas = document.getElementById("previewCanvas");
            previewCanvas.getContext('2d').save();
            $scope.paramsList[0].file=$scope.dataURLtoFile(previewCanvas.toDataURL(),$scope.fileName);
            $scope.thumb[0].imgSrc=previewCanvas.toDataURL();
            $('#exampleModalCenter').modal('hide');



        }

    }]);

})(angular, jQuery);
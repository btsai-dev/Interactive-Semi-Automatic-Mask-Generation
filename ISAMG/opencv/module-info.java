module opencv {
    requires java.logging;

    requires transitive java.desktop;

    exports nu.pattern;
    exports org.opencv.calib3d;
    exports org.opencv.core;
    exports org.opencv.dnn;
    exports org.opencv.features2d;
    exports org.opencv.highgui;
    exports org.opencv.imgcodecs;
    exports org.opencv.imgproc;
    exports org.opencv.ml;
    exports org.opencv.objdetect;
    exports org.opencv.osgi;
    exports org.opencv.photo;
    exports org.opencv.utils;
    exports org.opencv.video;
    exports org.opencv.videoio;

}

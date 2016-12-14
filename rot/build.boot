(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.5.2" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "0.6.0-19")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/rot
       :version     +version+
       :description "This is rot.js, the ROguelike Toolkit in JavaScript."
       :url         "http://ondras.github.io/rot.js/hp/"
       :license     {"BSD" "https://github.com/ondras/rot.js/blob/master/license.txt"}
       :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
   (download :url "https://github.com/ondras/rot.js/zipball/master"
             :checksum "4A9C06EAC5FA5111ABCAF6D75B1D82DA" ;;md5
             :unzip true)

   (sift :move {#"^ondras-rot.js-(.*)/rot.js" "cljsjs/rot/development/rot.inc.js"
                #"^ondras-rot.js-(.*)/rot.min.js" "cljsjs/rot/development/rot.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.rot")
   (pom)
   (jar)))

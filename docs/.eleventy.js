module.exports = function(eleventyConfig) {
  eleventyConfig.addTransform("htmlmin", function(content, outputPath) {
    if( outputPath.endsWith(".html") ) {
      return content.replace(/^\s*[\r\n]/gm, '');
    }

    return content;
  });

  eleventyConfig.templateFormats = [
    "html",
    "css"
  ];
};

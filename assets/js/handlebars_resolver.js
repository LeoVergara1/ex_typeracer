class HandlebarsResolver {
  constructor() {
    console.log("Inicia constructor handlebars")
    this._type = 'SingletonDefaultExportInstance';
  }

  static mergeViewWithModel(templateName, model){
    let source;
    source = $(templateName).html();
    template = Handlebars.compile(source);
    return template(model);
  }
  
  static testTamplate(){
    console.log("Init template ")
    let source = $("#run_area").html();
    let template = Handlebars.compile(source);
    console.log(template({hola:"kaka"}))
  }

  get type() {
    return this._type;
  }

  set type(value) {
    this._type = value;
  }
}

export default HandlebarsResolver = new HandlebarsResolver();
package sapsan.common


object FormButton extends Enumeration {
  type FormButton = Value
  val Save = Value("_save")
  val SaveAndAdd = Value("_saveAndAdd")
  val SaveAndEdit = Value("_saveAndEdit")
}
export default class TreeNode {
  constructor (name, children) {
    this.name = name
    this.parent = null
    if (children === undefined) {
      this.children = []
    } else {
      this.children = children
    }
  }

  addChild (node) {
    this.children.push(node)
    node.parent = this
  }

  removeChildAt (index) {
    this.children = this.children.splice(index, index)
  }
}

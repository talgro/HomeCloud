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
    // TODO: send an http request to the server to add this file.
  }

  deleteFile () {
    for (let i = 0; i < this.parent.children.length; ++i) {
      if (this.parent.children[i] === this) {
        this.parent.children.splice(i, 1)
        break
      }
    }

    // TODO: send an http request to the server to delete this file.
  }
}

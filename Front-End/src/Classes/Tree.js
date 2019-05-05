/* eslint-disable */
import TreeNode from './TreeNode.js'

export default class Tree {
  constructor (json) {
    this.root = new TreeNode('root')
    if (json === undefined) {
      this.children = []
    } else {
      this.root.children = this.buildSubTree(json.children)
    }
  }

  buildSubTree (json) {
    let sub_tree = []
    if (json.length === 0) {
      return []
    }
    for (let child in json) {
      let child_sub_tree = this.buildSubTree(child.children)
      let node = new TreeNode(child.name, child_sub_tree)
      sub_tree.push(node)
    }
    return sub_tree
  }
}

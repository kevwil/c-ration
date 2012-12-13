$ ->
  $("#tree").dynatree 
    initAjax:
      url: "/treedata.json"
    onClick: (node) ->
      window.open node.data.href, node.data.target if node.data.href
    
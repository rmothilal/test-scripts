/**
 * Script for csv files comparisson
 * Sample: `node csv-compare sample1.csv sample2.csv`
 * Sample: `node csv-compare sample1.csv:3 sample2.csv:2`
 *   - used only with config.match.type = 'key'. Column 3
 *   from sample1.csv is matched against Column 2
 */
const config = {
  src: {
    newLine: '\n',
    delimiter: ',',
    hasHeaderRow: false,
    qualifier: '"'
  },
  dst: {
    newLine: '\n',
    delimiter: ',',
    hasHeaderRow: true,
    qualifier: '"'
  },
  match: {
    // key - match specified column, default=1
    // line - match whole line
    // cols - match whole line after cleaning quoted
    // col - match both files by header row names
    type: 'key'
  },
  output: {
    newLine: '\n',
    delimiter: ',',
    clean: true, // clean quotes
    rowNum: true, // output matched/not-matched row number
    matched: false // output matched row content
  }
}

const fs = require('fs')
const util = require('util')

/**
 * Reads input csv file and returns an array of objects
 * @param {string} filename
 * @param {object} cfg
 * @param {int} keyCol
 */
function readCsv (filename, cfg, keyCol) {
  console.log('Reading', filename, '...')
  let res = []
  keyCol = typeof keyCol !== 'undefined' && keyCol > 0 ? keyCol - 1 : 0
  let text = fs.readFileSync(filename, 'utf8')
  let lines = text.split(cfg.newLine)
  let cols, lineObj, qRegExp, matches, tmpLine
  let hNames = []
  if (cfg.qualifier) {
    // non-greedy qualified text match
    qRegExp = new RegExp(cfg.qualifier + '.*?' + cfg.qualifier, 'g')
  }
  lines.forEach((line, i) => {
    if (!line.length) {
      return // skip blank lines
    }
    matches = null
    if (cfg.qualifier) {
      matches = line.match(qRegExp)
    }
    if (matches) { // when qualifier is used and match is found
      for (let i = 0; i < matches.length; i++) {
        matches[i] = matches[i].substring(cfg.delimiter.length, matches[i].length + 1 - cfg.delimiter.length * 2)
      }
      tmpLine = line.replace(qRegExp, '[$]')
      cols = tmpLine.split(cfg.delimiter)
      let ii = 0
      for (let i = 0; i < cols.length; i++) {
        if (cols[i] === '[$]') {
          cols[i] = matches[ii++]
        }
      }
    } else { // no qualifier or no match
      cols = line.split(cfg.delimiter)
    }
    if (cfg.hasHeaderRow && i === 0) {
      hNames = cols
      return
    }
    lineObj = {
      key: cols[keyCol],
      line,
      cols
    }
    cols.forEach((col, i) => {
      if (cfg.hasHeaderRow) {
        lineObj['col'] = {}
        if (hNames[i] !== undefined && hNames[i].length > 0) {
          lineObj['col'][hNames[i]] = col
        }
      }
    })
    lineObj.m = 0 // matched
    res.push(lineObj)
  })
  // console.log(res)
  return res
}

/**
 * Compares source and destination objects according to config
 * @param {object} source
 * @param {object} dest
 * @param {object} cfg
 */
function compare (source, dest, cfg) {
  console.log('Comparing files ...')
  let dstCol = true
  source.forEach((srcRow, i) => {
    if (cfg.type === 'col' && (typeof srcRow.col === 'undefined' || !dstCol)) {
      return false
    }
    dest.forEach((dstRow, j) => {
      if (dstRow.m) {
        return
      }
      if (cfg.type === 'key' && srcRow.key === dstRow.key) {
        srcRow.m = j + 1
        dstRow.m = i + 1
        return false
      } else if (cfg.type === 'line' && srcRow.line === dstRow.line) {
        srcRow.m = j + 1
        dstRow.m = i + 1
        return false
      } else if (cfg.type === 'cols' && srcRow.cols.join('|') === dstRow.cols.join('|')) {
        srcRow.m = j + 1
        dstRow.m = i + 1
        return false
      } else if (cfg.type === 'col') {
        if (typeof dstRow.col === 'undefined') {
          dstCol = false
          return false
        }
        if (util.inspect(srcRow.col) === util.inspect(dstRow.col)) {
          srcRow.m = j + 1
          dstRow.m = i + 1
          return false
        }
      }
    })
  })
}

/**
 * Writes the result of the comparisson into csv files
 * @param {object} source
 * @param {object} dest
 * @param {object} cfg
 * @param {string} sourceFilename
 * @param {string} destFilename
 */
function writeAll (source, dest, cfg, sourceFilename, destFilename) {
  console.log('Saving results ...')
  let srcMatchedCnt = 0
  let srcMatched = ''
  let srcNotMatchedCnt = 0
  let srcNotMatched = ''
  let dstMatchedCnt = 0
  let dstMatched = ''
  let dstNotMatchedCnt = 0
  let dstNotMatched = ''
  source.forEach((srcRow, i) => {
    srcMatched += srcRow.m > 0 ? ((cfg.clean ? srcRow.cols.join(cfg.delimiter) : srcRow.line) + (cfg.rowNum ? cfg.delimiter + srcRow.m : '') +
      (cfg.matched ? cfg.delimiter + (cfg.clean ? dest[srcRow.m - 1].cols.join(cfg.delimiter) : dest[srcRow.m - 1].line) : '') + cfg.newLine) : ''
    srcNotMatched += srcRow.m === 0 ? ((cfg.clean ? srcRow.cols.join(cfg.delimiter) : srcRow.line) + (cfg.rowNum ? cfg.delimiter + (i + 1) : '') +
      cfg.newLine) : ''
    if (srcRow.m > 0) {
      srcMatchedCnt++
    } else {
      srcNotMatchedCnt++
    }
  })
  dest.forEach((dstRow, i) => {
    dstMatched += dstRow.m > 0 ? ((cfg.clean ? dstRow.cols.join(cfg.delimiter) : dstRow.line) + (cfg.rowNum ? cfg.delimiter + dstRow.m : '') +
      (cfg.matched ? cfg.delimiter + (cfg.clean ? source[dstRow.m - 1].cols.join(cfg.delimiter) : source[dstRow.m - 1].line) : '') + cfg.newLine) : ''
    dstNotMatched += dstRow.m === 0 ? ((cfg.clean ? dstRow.cols.join(cfg.delimiter) : dstRow.line) + (cfg.rowNum ? cfg.delimiter + (i + 1) : '') +
      cfg.newLine) : ''
    if (dstRow.m > 0) {
      dstMatchedCnt++
    } else {
      dstNotMatchedCnt++
    }
  })
  let srcFnArr = sourceFilename.split('.')
  let srcFnExt = srcFnArr.pop()
  let dstFnArr = destFilename.split('.')
  let dstFnExt = dstFnArr.pop()
  let fn1 = srcFnArr.join('.') + '.matched-' + srcMatchedCnt + '.' + dstFnArr.join('.') + '.' + srcFnExt
  fs.writeFile(fn1, srcMatched, (err) => {
    if (err) throw err
    console.log(fn1, 'has been saved (' + srcMatchedCnt + ' records)')
  })
  let fn2 = srcFnArr.join('.') + '.not-matched-' + srcNotMatchedCnt + '.' + dstFnArr.join('.') + '.' + srcFnExt
  fs.writeFile(fn2, srcNotMatched, (err) => {
    if (err) throw err
    console.log(fn2, 'has been saved (' + srcNotMatchedCnt + ' records)')
  })
  let fn3 = dstFnArr.join('.') + '.matched-' + dstMatchedCnt + '.' + srcFnArr.join('.') + '.' + dstFnExt
  fs.writeFile(fn3, dstMatched, (err) => {
    if (err) throw err
    console.log(fn3, 'has been saved (' + dstMatchedCnt + ' records)')
  })
  let fn4 = dstFnArr.join('.') + '.not-matched-' + dstNotMatchedCnt + '.' + srcFnArr.join('.') + '.' + dstFnExt
  fs.writeFile(fn4, dstNotMatched, (err) => {
    if (err) throw err
    console.log(fn4, 'has been saved (' + dstNotMatchedCnt + ' records)')
  })
}

/**
 * Main execution body of the script
 */
var args = process.argv.slice(2)
if (typeof args[0] === 'undefined') {
  console.error('Please, specify source file!')
} else if (typeof args[1] === 'undefined') {
  console.error('Please, specify destination file!')
} else {
  const srcArg = args[0].split(':')
  const dstArg = args[1].split(':')
  const srcFilename = srcArg[0]
  const dstFilename = dstArg[0]
  const srcCol = srcArg[1] ? srcArg[1] : 0
  const dstCol = dstArg[1] ? dstArg[1] : 0
  try {
    let src = readCsv(srcFilename, config.src, srcCol)
    let dst = readCsv(dstFilename, config.dst, dstCol)
    compare(src, dst, config.match)
    writeAll(src, dst, config.output, srcFilename, dstFilename)
  } catch (err) {
    console.error(err.message + '!')
  }
}

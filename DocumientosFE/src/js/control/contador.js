let count = 0;

self.onmessage = function(e) {
    count+=1;
    console.log(e.value)
    self.postMessage(count);
}
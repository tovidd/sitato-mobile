<?php

Route::get('/', function () {
    $data['base_url']= "http://toviddd.xyz/";
    return view('api', compact('data'));
});


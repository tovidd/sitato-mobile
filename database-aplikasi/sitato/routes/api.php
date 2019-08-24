<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::resource('sparepart', 'sparepartController', ['except'=> ['create', 'edit']]);
Route::resource('role', 'roleController', ['except'=> ['create', 'edit']]);
Route::resource('supplier', 'supplierController', ['except'=> ['create', 'edit']]);
Route::resource('cabang', 'cabangController', ['except'=> ['create', 'edit']]);
Route::resource('jasaService', 'jasaServiceController', ['except'=> ['create', 'edit']]);
Route::resource('kendaraan', 'kendaraanController', ['except'=> ['create', 'edit']]);
Route::resource('pelanggan', 'pelangganController', ['except'=> ['create', 'edit']]);
Route::resource('statusPengerjaan', 'statusPengerjaanController', ['except'=> ['create', 'edit']]);
Route::resource('pegawai', 'pegawaiController', ['except'=> ['create', 'edit']]);
    Route::post('pegawai/login','pegawaiController@login'); 

Route::resource('pengadaanSparepart', 'pengadaanSparepartController', ['except'=> ['create', 'edit']]);
Route::resource('detilPengadaanSparepart', 'detilPengadaanSparepartController', ['except'=> ['create', 'edit']]);
Route::resource('transaksi', 'transaksiController', ['except'=> ['create', 'edit']]);
Route::resource('transaksiPenjualanJasaService', 'transaksiPenjualanJasaServiceController', ['except'=> ['create', 'edit']]);
Route::resource('transaksiPenjualanSparepart', 'transaksiPenjualanSparepartController', ['except'=> ['create', 'edit']]);

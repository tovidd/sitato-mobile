<?php

namespace sitato\Http\Controllers;

use sitato\transaksiPenjualanJasaService;
use Illuminate\Http\Request;

class transaksiPenjualanJasaServiceController extends Controller
{
    public function index() 
    {
        $data= transaksiPenjualanJasaService::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new transaksiPenjualanJasaService;
        $data->id_transaksi= $request->id_transaksi;
        $data->id_montir= $request->id_montir;
        $data->id_jasa_service= $request->id_jasa_service;
        $data->id_kendaraan= $request->id_kendaraan;
        $data->jumlah_transaksi_penjualan_jasa_service= $request->jumlah_transaksi_penjualan_jasa_service;
        $data->subtotal_transaksi_penjualan_jasa_service= $request->subtotal_transaksi_penjualan_jasa_service;
        
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    public function show($id) 
    {
        $data= transaksiPenjualanJasaService::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(transaksiPenjualanJasaService $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= jasaService::find($id);
        if(!is_null($request->id_transaksi))
        {
            $data->id_transaksi= $request->id_transaksi;
        }
        if(!is_null($request->id_montir))
        {
            $data->id_montir= $request->id_montir;
        }
        if(!is_null($request->id_kendaraan))
        {
            $data->id_jasa_service= $request->id_kendaraan;
        }
        if(!is_null($request->kode_jasa_service))
        {
            $data->id_kendaraan= $request->id_kendaraan;
        }
        if(!is_null($request->jumlah_transaksi_penjualan_jasa_service))
        {
            $data->jumlah_transaksi_penjualan_jasa_service= $request->jumlah_transaksi_penjualan_jasa_service;
        }
        if(!is_null($request->subtotal_transaksi_penjualan_jasa_service))
        {
            $data->subtotal_transaksi_penjualan_jasa_service= $request->subtotal_transaksi_penjualan_jasa_service;
        }        

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    public function destroy($id)
    {
        $data= jasaService::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}

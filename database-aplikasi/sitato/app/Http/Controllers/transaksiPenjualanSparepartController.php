<?php

namespace sitato\Http\Controllers;

use sitato\transaksiPenjualanSparepart;
use Illuminate\Http\Request;

class transaksiPenjualanSparepartController extends Controller
{
    public function index() 
    {
        $data= transaksiPenjualanSparepart::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new transaksiPenjualanSparepart;
        $data->id_transaksi= $request->id_transaksi;
        $data->id_sparepart= $request->id_sparepart;
        $data->jumlah_transaksi_penjualan_sparepart= $request->jumlah_transaksi_penjualan_sparepart;
        $data->subtotal_transaksi_penjualan_sparepart= $request->subtotal_transaksi_penjualan_sparepart;
        
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
        $data= transaksiPenjualanSparepart::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(transaksiPenjualanSparepart $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= transaksiPenjualanSparepart::find($id);
        if(!is_null($request->id_transaksi))
        {
            $data->id_transaksi= $request->id_transaksi;
        }
        if(!is_null($request->id_sparepart))
        {
            $data->id_sparepart= $request->id_sparepart;
        }
        if(!is_null($request->jumlah_transaksi_penjualan_sparepart))
        {
            $data->jumlah_transaksi_penjualan_sparepart= $request->jumlah_transaksi_penjualan_sparepart;
        }
        if(!is_null($request->subtotal_transaksi_penjualan_sparepart))
        {
            $data->subtotal_transaksi_penjualan_sparepart= $request->subtotal_transaksi_penjualan_sparepart;
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
        $data= transaksiPenjualanSparepart::find($id);
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

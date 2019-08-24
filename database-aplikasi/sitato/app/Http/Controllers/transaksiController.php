<?php

namespace sitato\Http\Controllers;

use sitato\transaksi;
use Illuminate\Http\Request;

class transaksiController extends Controller
{
    public function index() 
    {
        $data= transaksi::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new transaksi;
        $data->id_pelanggan= $request->id_pelanggan;
        $data->id_cs= $request->id_cs;
        $data->id_kasir= $request->id_kasir;
        $data->id_status_pengerjaan= $request->id_status_pengerjaan;
        $data->total_transaksi= $request->total_transaksi;
        $data->diskon_transaksi= $request->diskon_transaksi;
        $data->jumlah_uang_pembayaran_transaksi= $request->jumlah_uang_pembayaran_transaksi;
        
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
        $data= transaksi::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(transaksi $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= transaksi::find($id);
        if(!is_null($request->id_pelanggan))
        {
            $data->id_pelanggan= $request->id_pelanggan;
        }
        if(!is_null($request->id_cs))
        {
            $data->id_cs= $request->id_cs;
        }
        if(!is_null($request->id_kasir))
        {
            $data->id_kasir= $request->id_kasir;
        }
        if(!is_null($request->id_status_pengerjaan))
        {
            $data->id_status_pengerjaan= $request->id_status_pengerjaan;
        }
        if(!is_null($request->total_transaksi))
        {
            $data->total_transaksi= $request->total_transaksi;
        }
        if(!is_null($request->diskon_transaksi))
        {
            $data->diskon_transaksi= $request->diskon_transaksi;
        }
        if(!is_null($request->jumlah_uang_pembayaran_transaksi))
        {
            $data->jumlah_uang_pembayaran_transaksi= $request->jumlah_uang_pembayaran_transaksi;
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
        $data= transaksi::find($id);
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
